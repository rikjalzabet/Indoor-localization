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
//builder.Services.AddScoped<IRepository<FloorMap>, MockFloorMapRepository>();
//builder.Services.AddScoped<IRepository<Zone>, MockZoneRepository>();

builder.Services.AddScoped<IAssetRepository, AssetRepository>();
builder.Services.AddScoped<IFloorMapRepository, FloorMapRepository>();
builder.Services.AddScoped<IZoneRepository, ZoneRepository>();

builder.Services.AddSingleton<IAssetPositionHistoryRepository, MockAssetPositionHistoryRepository>();

//////// SERVICES

builder.Services.AddScoped<IAssetService, AssetService>();
builder.Services.AddScoped<IZoneService, ZoneService>();
builder.Services.AddScoped<IFloorMapService, FloorMapService>();
builder.Services.AddSingleton<IAssetPositionHistoryService, AssetPositionHistoryService>();

var app = builder.Build();

using (var scope = app.Services.CreateScope())
{
    var context = scope.ServiceProvider.GetRequiredService<AppDbContext>();
}

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
