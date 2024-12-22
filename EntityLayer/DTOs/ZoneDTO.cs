using EntityLayer.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;

namespace EntityLayer.DTOs
{
    public class ZoneDTO
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public List<Point> Points { get; set; }
    }
}
