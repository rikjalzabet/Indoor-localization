﻿using BusinessLogicLayer.Interfaces;
using DataAccessLayer.Interfaces;
using DataAccessLayer.Repositories;
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
                .WithTcpServer("broker.hivemq.com", 1883)
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
                    var positionService = scope.ServiceProvider.GetRequiredService<IAssetPositionHistoryService>();
                    var zoneService = scope.ServiceProvider.GetRequiredService<IAssetZoneHistoryService>();
                    var assetService = scope.ServiceProvider.GetRequiredService<IAssetService>();


                    await SavePositionToDatabaseAsync(positionService, zoneService, assetService, positionData, stoppingToken);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Greška prilikom obrade poruke: {ex.Message}");
            }
        }

        private async Task SavePositionToDatabaseAsync(IAssetPositionHistoryService positionService, IAssetZoneHistoryService zoneService, IAssetService assetService, AssetPositionHistory positionData, CancellationToken stoppingToken)
        {
            await positionService.AddAssetPositionHistory(positionData);

            await UpdateZoneHistory(zoneService, positionData);

            await UpdateAssetPosition(assetService, positionData);

            Console.WriteLine("Spremam poziciju u bazu i ažuriram povijest zona!");
            await Task.CompletedTask;
        }

        private async Task UpdateZoneHistory(IAssetZoneHistoryService zoneService, AssetPositionHistory positionData)
        {
            await zoneService.UpdateZoneHistory(positionData);
            await Task.CompletedTask;
        }
        private async Task UpdateAssetPosition(IAssetService assetService, AssetPositionHistory positionData)
        {
            await assetService.UpdateAssetPosition(positionData);
        }
    }
}
