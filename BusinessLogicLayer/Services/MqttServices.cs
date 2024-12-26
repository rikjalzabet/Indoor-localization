using DataAccessLayer.Interfaces;
using EntityLayer.Entities;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using MQTTnet;
using MQTTnet.Client;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BusinessLogicLayer.Services
{
    public class MqttBackgroundService : BackgroundService
    {
        private readonly IMqttClient _mqttClient;
        private readonly IServiceProvider _serviceProvider; 

        public MqttBackgroundService(IServiceProvider serviceProvider)
        {
            _serviceProvider = serviceProvider;
            var mqttFactory = new MqttFactory();
            _mqttClient = mqttFactory.CreateMqttClient();
        }

        protected override async Task ExecuteAsync(CancellationToken stoppingToken)
        {
            var options = new MqttClientOptionsBuilder()
                .WithClientId("DotNetApiClient")
                .WithTcpServer("broker.emqx.io", 1883)
                .Build();

            try
            {
                await _mqttClient.ConnectAsync(options, stoppingToken);
                Console.WriteLine("Successfully connected to the broker.");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error connecting to broker: {ex.Message}");
                return;
            }

            await _mqttClient.SubscribeAsync(new MqttTopicFilterBuilder()
                .WithTopic("rtls/positions")
                .Build(), stoppingToken);

            _mqttClient.ApplicationMessageReceivedAsync += async e =>
            {
                Console.WriteLine($"Received message on topic: {e.ApplicationMessage.Topic}");
                var payload = Encoding.UTF8.GetString(e.ApplicationMessage.Payload);
                Console.WriteLine($"Payload: {payload}");

                await HandleIncomingMessageAsync(payload, stoppingToken);
            };

            await Task.Delay(Timeout.Infinite, stoppingToken);
        }

        private async Task HandleIncomingMessageAsync(string payload, CancellationToken stoppingToken)
        {
            try
            {
                var positionData = JsonConvert.DeserializeObject<AssetPositionHistory>(payload);

                if (positionData != null)
                {
                    using var scope = _serviceProvider.CreateScope();
                    var repository = scope.ServiceProvider.GetRequiredService<IAssetPositionHistoryRepository>();

                    await SavePositionToDatabaseAsync(repository, positionData, stoppingToken);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Greška prilikom obrade poruke: {ex.Message}");
            }
        }

        private async Task SavePositionToDatabaseAsync(IAssetPositionHistoryRepository repository, AssetPositionHistory positionData, CancellationToken stoppingToken)
        {
            await repository.AddAssetPositionHistory(positionData);

            Console.WriteLine("Spremam poziciju u bazu!");
            await Task.CompletedTask;
        }
    }
}
