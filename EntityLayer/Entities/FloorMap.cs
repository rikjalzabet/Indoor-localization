using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EntityLayer.Entities
{
    public class FloorMap
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Image { get; set; }

        //public virtual ICollection<Asset> Assets { get; set; } = new List<Asset>();
        //public virtual ICollection<AssetPositionHistory> AssetPositionHistories { get; set; } = new List<AssetPositionHistory>();

    }
}
