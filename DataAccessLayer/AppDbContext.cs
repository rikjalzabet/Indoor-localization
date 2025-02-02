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
        public DbSet<AssetZoneHistory> AssetZoneHistory { get; set; }


        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {

            foreach (var entity in modelBuilder.Model.GetEntityTypes())
            {
                entity.SetTableName(entity.GetTableName().ToLower());

                foreach (var property in entity.GetProperties())
                {
                    property.SetColumnName(property.GetColumnName().ToLower());
                }
            }

            modelBuilder.Entity<Asset>(entity =>
            {
                entity.HasKey(a => a.Id);
                entity.HasOne(a => a.FloorMap)
                      .WithMany(/*f => f.Assets*/)
                      .HasForeignKey(a => a.FloorMapId)
                      .OnDelete(DeleteBehavior.Cascade);
                entity.Property(a => a.Id)
                .ValueGeneratedOnAdd();
            });

            // FloorMap konfiguracija
            modelBuilder.Entity<FloorMap>(entity =>
            {
                entity.HasKey(f => f.Id);
                entity.Property(f => f.Id)
                .ValueGeneratedOnAdd();
            });

            // Zone konfiguracija
            modelBuilder.Entity<Zone>(entity =>
            {
                entity.HasKey(z => z.Id);
                entity.Property(z => z.Points).HasColumnType("json");
                entity.Property(z => z.Id)
                .ValueGeneratedOnAdd();
            });

            // AssetPositionHistory konfiguracija
            modelBuilder.Entity<AssetPositionHistory>(entity =>
            {
                entity.HasKey(a => a.Id);
                entity.Property(a => a.Id)
                .ValueGeneratedOnAdd();
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
            modelBuilder.Entity<AssetZoneHistory>(entity =>
            {
                entity.HasKey(a => a.Id);
                entity.Property(a => a.Id)
                .ValueGeneratedOnAdd();
                /*entity.HasOne(a => a.Asset)
                      .WithMany()
                      .HasForeignKey(a => a.AssetId)
                      .OnDelete(DeleteBehavior.Cascade);

                entity.HasOne(a => a.Zone)
                      .WithMany()
                      .HasForeignKey(a => a.ZoneId);*/
            });
            
            base.OnModelCreating(modelBuilder);

        }

    }

}
