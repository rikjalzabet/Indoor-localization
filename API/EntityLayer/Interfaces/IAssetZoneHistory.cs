using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EntityLayer.Interfaces
{
    public interface IAssetZoneHistory
    {
        public int Id { get; set; }
        public int AssetId { get; set; }
        public int ZoneId { get; set; }
        public DateTime EnterDateTime { get; set; }
        public DateTime? ExitDateTime { get; set; }
        public TimeSpan? RetentionTime { get; set; }
    }
}
