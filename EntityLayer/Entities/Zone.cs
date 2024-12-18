using EntityLayer.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EntityLayer.Entities
{
    public class Zone : IZone
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public List<Point> Points { get; set; }
    }
}
