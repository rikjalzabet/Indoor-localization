using EntityLayer.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Nodes;
using System.Threading.Tasks;

namespace EntityLayer.Interfaces
{
    public interface IZone
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public List<Point> Points { get; set; }
    }
}
