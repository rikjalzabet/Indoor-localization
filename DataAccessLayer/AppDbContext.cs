using EntityLayer.Entities;
using EntityLayer.Interfaces;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLayer
{
    public class AppDbContext : DbContext
    {

        public AppDbContext(DbContextOptions<AppDbContext> options)
      : base(options)
        {
        }
        public DbSet<Zone> Zones { get; set; }
        public DbSet<Asset> Assets { get; set; }
        public DbSet<FloorMap> FloorMaps { get; set; }

        public DbSet<AssetPositionHistory> AssetPositionHistory { get; set; }
        //public DbSet<AssetZoneHistory> AssetZoneHistory { get; set; }


        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {

            modelBuilder.Entity<Asset>(entity =>
            {
                entity.HasKey(a => a.Id);
                entity.HasOne(a => a.FloorMap)
                      .WithMany(/*f => f.Assets*/)
                      .HasForeignKey(a => a.FloorMapId)
                      .OnDelete(DeleteBehavior.Cascade);
            });

            // FloorMap konfiguracija
            modelBuilder.Entity<FloorMap>(entity =>
            {
                entity.HasKey(f => f.Id);
            });

            // Zone konfiguracija
            modelBuilder.Entity<Zone>(entity =>
            {
                entity.HasKey(z => z.Id);
                entity.Property(z => z.Points).HasColumnType("json");
            });

            // AssetPositionHistory konfiguracija
            modelBuilder.Entity<AssetPositionHistory>(entity =>
            {
                entity.HasKey(a => a.Id);
                /*
                entity.HasOne(a => a.Asset)
                      .WithMany()
                      .HasForeignKey(a => a.AssetId)
                      .OnDelete(DeleteBehavior.Cascade);

                entity.HasOne(a => a.FloorMap)
                      .WithMany()
                      .HasForeignKey(a => a.FloorMapId);*/
            });

            // AssetZoneHistory konfiguracija
            /*modelBuilder.Entity<AssetZoneHistory>(entity =>
            {
                entity.HasKey(a => a.Id);
                entity.HasOne(a => a.Asset)
                      .WithMany()
                      .HasForeignKey(a => a.AssetId)
                      .OnDelete(DeleteBehavior.Cascade);

                entity.HasOne(a => a.Zone)
                      .WithMany()
                      .HasForeignKey(a => a.ZoneId);
            });
            */
            base.OnModelCreating(modelBuilder);

        }

    }

}
