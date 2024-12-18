using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EntityLayer.Entities
{
    public class AssetPositionHistory
    {
        public int Id { get; set; }
        public int AssetId { get; set; }
        public double X { get; set; }
        public double Y { get; set; }
        public DateTime DateTime { get; set; }
        public int FloorMapId { get; set; }

        public Asset Asset { get; set; }
        public FloorMap FloorMap { get; set; }
    }
}
