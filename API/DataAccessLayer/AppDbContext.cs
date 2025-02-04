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
                      .WithMany()
                      .HasForeignKey(a => a.FloorMapId)
                      .OnDelete(DeleteBehavior.Cascade);
                entity.Property(a => a.Id)
                .ValueGeneratedOnAdd();
            });

            modelBuilder.Entity<FloorMap>(entity =>
            {
                entity.HasKey(f => f.Id);
                entity.Property(f => f.Id)
                .ValueGeneratedOnAdd();
            });

            modelBuilder.Entity<Zone>(entity =>
            {
                entity.HasKey(z => z.Id);
                entity.Property(z => z.Points).HasColumnType("json");
                entity.Property(z => z.Id)
                .ValueGeneratedOnAdd();
            });

            modelBuilder.Entity<AssetPositionHistory>(entity =>
            {
                entity.HasKey(a => a.Id);
                entity.Property(a => a.Id)
                .ValueGeneratedOnAdd();
            });

            modelBuilder.Entity<AssetZoneHistory>(entity =>
            {
                entity.HasKey(a => a.Id);
                entity.Property(a => a.Id)
                .ValueGeneratedOnAdd();
            });
            
            base.OnModelCreating(modelBuilder);

        }

    }

}
