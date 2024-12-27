using BusinessLogicLayer.Interfaces;
using DataAccessLayer.Interfaces;
using EntityLayer.Entities;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;

namespace BusinessLogicLayer.Services
{
    public class AssetZoneHistoryService : IAssetZoneHistoryService
    {
        private readonly IAssetZoneHistoryRepository _assetZoneHistoryRepository;
        private readonly IZoneRepository _zoneRepository;
        public AssetZoneHistoryService(IAssetZoneHistoryRepository assetZoneHistoryRepository, IZoneRepository zoneRepository)
        {
            _assetZoneHistoryRepository = assetZoneHistoryRepository;
            _zoneRepository = zoneRepository;
        }

        public async Task UpdateZoneHistory(AssetPositionHistory position)
        {
            var zones = await _zoneRepository.GetAllAsync();

            var currentZone = zones.FirstOrDefault(zone =>
            {
                var points = DeserializePoints(zone.Points);
                return IsPointInsidePolygon(position.X, position.Y, points);
            });

            var lastAssetZoneHistory = await _assetZoneHistoryRepository.GetLatesByAssetId(position.AssetId);

            if (currentZone == null)
            {
                if (lastAssetZoneHistory != null && lastAssetZoneHistory.ExitDateTime == null)
                {
                    lastAssetZoneHistory.ExitDateTime = DateTime.UtcNow;
                    lastAssetZoneHistory.RetentionTime = lastAssetZoneHistory.ExitDateTime - lastAssetZoneHistory.EnterDateTime;
                    await _assetZoneHistoryRepository.UpdateZoneHistory(lastAssetZoneHistory);
                }
            }
            else
            {
                // Ako je asset u novoj zoni
                if (lastAssetZoneHistory == null || lastAssetZoneHistory.ZoneId != currentZone.Id)
                {
                    // Zatvori prethodnu zonu (ako postoji)
                    if (lastAssetZoneHistory != null && lastAssetZoneHistory.ExitDateTime == null)
                    {
                        lastAssetZoneHistory.ExitDateTime = DateTime.UtcNow;
                        lastAssetZoneHistory.RetentionTime = lastAssetZoneHistory.ExitDateTime - lastAssetZoneHistory.EnterDateTime;
                        await _assetZoneHistoryRepository.UpdateZoneHistory(lastAssetZoneHistory);
                    }

                    // Kreiraj novi zapis za trenutnu zonu
                    var newAssetZoneHistory = new AssetZoneHistory
                    {
                        AssetId = position.AssetId,
                        ZoneId = currentZone.Id,
                        EnterDateTime = DateTime.UtcNow
                    };
                    await _assetZoneHistoryRepository.AddAsync(newAssetZoneHistory);
                }
            }
        }
        private List<Point> DeserializePoints(JsonDocument jsonDocument)
        {
            if (jsonDocument == null)
                return new List<Point>();

            var jsonString = jsonDocument.RootElement.GetRawText();
            return JsonConvert.DeserializeObject<List<Point>>(jsonString);
        }


        public bool IsPointInsidePolygon(double x, double y, List<Point> polygon)
        {
            int intersections = 0;
            int count = polygon.Count;

            for (int i = 0; i < count; i++)
            {
                Point vertex1 = polygon[i];
                Point vertex2 = polygon[(i + 1) % count];

                if ((vertex1.Y > y) != (vertex2.Y > y))
                {
                    double intersectX = (vertex2.X - vertex1.X) * (y - vertex1.Y) / (vertex2.Y - vertex1.Y) + vertex1.X;

                    if (x < intersectX)
                    {
                        intersections++;
                    }
                }
            }

            return (intersections % 2) != 0;
        }

    }
}
