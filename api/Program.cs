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

// Add services to the container.

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();


// zamijeniti sa AddScoped nakon kreiranja EFC-a

/////// REPOSITORIES

//builder.Services.AddScoped<IRepository<Asset>, MockAssetRepository>();
builder.Services.AddScoped<IAssetRepository, AssetRepository>();

builder.Services.AddSingleton<IZoneRepository, MockZoneRepository>();
builder.Services.AddSingleton<IFloorMapRepository, MockFloorMapRepository>();
builder.Services.AddSingleton<IAssetPositionHistoryRepository, MockAssetPositionHistoryRepository>();

//////// SERVICES

builder.Services.AddScoped<IAssetService, AssetService>();
builder.Services.AddSingleton<IZoneService, ZoneService>();
builder.Services.AddSingleton<IFloorMapService, FloorMapService>();
builder.Services.AddSingleton<IAssetPositionHistoryService, AssetPositionHistoryService>();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();
