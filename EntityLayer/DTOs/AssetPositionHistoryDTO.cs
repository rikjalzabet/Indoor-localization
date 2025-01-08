using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EntityLayer.DTOs
{
    public class AssetPositionHistoryDTO
    {
        public int AssetId { get; set; }
        public double X { get; set; }
        public double Y { get; set; }
        public DateTime DateTime { get; set; }
        public int FloorMapId { get; set; }
    }
}
