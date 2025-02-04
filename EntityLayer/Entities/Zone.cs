using EntityLayer.Interfaces;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;

namespace EntityLayer.Entities
{
    public class Zone : IZone
    {
        [Key]
        public int Id { get; set; }
        public string Name { get; set; }
        public JsonDocument Points { get; set; }

    }
}
