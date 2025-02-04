using BusinessLogicLayer.Interfaces;
using BusinessLogicLayer.Services;
using DataAccessLayer;
using DataAccessLayer.Interfaces;
using DataAccessLayer.MockRepositories;
using DataAccessLayer.Repositories;
using EntityLayer.Entities;
using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddDbContext<AppDbContext>(options =>
    options.UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection")));

builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

builder.Services.AddCors(options =>
{
    options.AddPolicy("AllowSpecificOrigins", policy =>
    {
        policy.WithOrigins("http://localhost:4200") 
              .AllowAnyHeader()                   
              .AllowAnyMethod();                  
    });
});

builder.Services.AddScoped<IAssetRepository, AssetRepository>();
builder.Services.AddScoped<IFloorMapRepository, FloorMapRepository>();
builder.Services.AddScoped<IZoneRepository, ZoneRepository>();
builder.Services.AddScoped<IAssetPositionHistoryRepository, AssetPositionHistoryRepository>();
builder.Services.AddScoped<IAssetZoneHistoryRepository, AssetZoneHistoryRepository>();

builder.Services.AddScoped<IAssetService, AssetService>();
builder.Services.AddScoped<IZoneService, ZoneService>();
builder.Services.AddScoped<IFloorMapService, FloorMapService>();
builder.Services.AddScoped<IAssetPositionHistoryService, AssetPositionHistoryService>();
builder.Services.AddScoped<IAssetZoneHistoryService, AssetZoneHistoryService>();

builder.Services.AddHostedService<MqttBackgroundService>();


var app = builder.Build();

using (var scope = app.Services.CreateScope())
{
    var context = scope.ServiceProvider.GetRequiredService<AppDbContext>();
}

if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseCors("AllowSpecificOrigins");

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();
