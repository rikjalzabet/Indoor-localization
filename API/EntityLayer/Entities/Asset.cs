﻿using EntityLayer.Interfaces;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EntityLayer.Entities
{
    public class Asset:IAsset
    {
        [Key]
        public int Id { get; set; }
        public string Name { get; set; }
        public double X { get; set; }
        public double Y { get; set; }
        public DateTime LastSync { get; set; }
        public int FloorMapId { get; set; }
        public bool Active { get; set; }

        public virtual FloorMap FloorMap { get; set; }
        //public virtual ICollection<AssetPositionHistory> AssetPositionHistories { get; set; } = new List<AssetPositionHistory>();


    }
}
