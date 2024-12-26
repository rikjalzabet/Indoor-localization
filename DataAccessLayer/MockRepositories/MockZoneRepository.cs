using DataAccessLayer.Interfaces;
using EntityLayer.Entities;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;

namespace DataAccessLayer.MockRepositories
{
    public class MockZoneRepository : IRepository<Zone>
    {

        private readonly List<Zone> _zones = new()
        {
            new Zone
            {
                Id = 1,
                Name = "Zone 1",
                Points = JsonDocument.Parse(@"
                {
                    ""points"": [
                        { ""X"": 10.5, ""Y"": 20.5 },
                        { ""X"": 15.5, ""Y"": 25.5 },
                        { ""X"": 10.5, ""Y"": 30.5 }
                    ]
                }")
            },
            new Zone
            {
                Id = 2,
                Name = "Zone 2",
                Points = JsonDocument.Parse(@"
                {
                    ""points"": [
                        { ""X"": 5.0, ""Y"": 10.0 },
                        { ""X"": 7.0, ""Y"": 12.0 },
                        { ""X"": 9.0, ""Y"": 8.0 }
                    ]
                }")
            },
            new Zone
            {
                Id = 3,
                Name = "Zone 3",
                Points = JsonDocument.Parse(@"
                {
                    ""points"": [
                        { ""X"": 1.5, ""Y"": 3.5 },
                        { ""X"": 4.5, ""Y"": 5.5 },
                        { ""X"": 2.5, ""Y"": 6.5 }
                    ]
                }")
            }
        };



        public async Task<IEnumerable<Zone>> GetAllAsync()
        {
            return await Task.FromResult(_zones);
        }

        public async Task<Zone> GetByIdAsync(int id)
        {
            return await Task.FromResult(_zones.FirstOrDefault(z => z.Id == id));

        }

        public async Task<int> AddAsync(Zone zone)
        {
            var existingZone = _zones.FirstOrDefault(z => z.Id == zone.Id);
            if (existingZone == null)
            {
                _zones.Add(zone);
                return 1;
            }
            return 0;
        }

        public async Task<int> DeleteAsync(int id)
        {
            var existingZone = _zones.FirstOrDefault(z => z.Id == id);
            if (existingZone == null)
                return 0;
            _zones.Remove(existingZone);
            return 1;
        }

        public async Task<int> UpdateZone(Zone zone, int id)
        {
            var existingZone = _zones.FirstOrDefault(z => z.Id == id);

            if (existingZone == null)
                return 0;

            existingZone.Id = zone.Id;
            existingZone.Name = zone.Name;
            existingZone.Points = zone.Points;

            return 1;
        }
    }
}
