using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EntityLayer.DTOs
{
    public class AssetDTO
    {
        public string Name { get; set; }
        public double X { get; set; }
        public double Y { get; set; }
        public DateTime LastSync { get; set; }
        public int FloorMapId { get; set; }
        public bool Active { get; set; }
    }
}
