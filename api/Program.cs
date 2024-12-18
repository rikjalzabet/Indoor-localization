using BusinessLogicLayer.Interfaces;
using BusinessLogicLayer.Services;
using DataAccessLayer.Interfaces;
using DataAccessLayer.MockRepositories;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();


// zamijeniti sa AddScoped nakon kreiranja EFC-a

builder.Services.AddSingleton<IAssetRepository, MockAssetRepository>();
builder.Services.AddSingleton<IZoneRepository, MockZoneRepository>();
builder.Services.AddSingleton<IFloorMapRepository, MockFloorMapRepository>();
builder.Services.AddSingleton<IAssetService, AssetService>();
builder.Services.AddSingleton<IZoneService, ZoneService>();
builder.Services.AddSingleton<IFloorMapService, FloorMapService>();


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
