﻿using DataAccessLayer.Interfaces;
using EntityLayer.Entities;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLayer.Repositories
{
    public class AssetRepository : Repository<Asset>, IAssetRepository
    {
        public AssetRepository(AppDbContext context) : base(context)
        {

        }
        public async Task<int> UpdateAsset(Asset asset, int id)
        {
            var existingAsset = await Entities.FindAsync(id);
            if (existingAsset != null)
            {
                existingAsset.Name = asset.Name;
                existingAsset.X = asset.X;
                existingAsset.Y = asset.Y;
                existingAsset.LastSync = asset.LastSync;
                existingAsset.FloorMapId = asset.FloorMapId;
                existingAsset.Active = asset.Active;
            }

            return await _context.SaveChangesAsync();
        }
        public async Task<int> UpdateAssetPosition(AssetPositionHistory assetPositionHistory)
        {
            var existingAsset = await Entities.FindAsync(assetPositionHistory.AssetId);
            if (existingAsset.FloorMapId != assetPositionHistory.FloorMapId)
            {
                existingAsset = null;
            }

            if (existingAsset != null)
            {
                existingAsset.X = assetPositionHistory.X;
                existingAsset.Y = assetPositionHistory.Y;
                existingAsset.LastSync = DateTime.UtcNow;
            }
            return await _context.SaveChangesAsync();
        }
    }
}
