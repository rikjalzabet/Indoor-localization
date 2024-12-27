using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EntityLayer.Entities
{
    public class AssetZoneHistory
    {
        public int Id { get; set; }
        public int AssetId { get; set; }
        public int ZoneId { get; set; }
        public DateTime EnterDateTime { get; set; }
        public DateTime? ExitDateTime { get; set; }
        public TimeSpan? RetentionTime { get; set; }

        public virtual Asset Asset { get; set; }
        public virtual Zone Zone { get; set; }
    }
}
