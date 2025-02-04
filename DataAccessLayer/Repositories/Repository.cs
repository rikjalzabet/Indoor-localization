using DataAccessLayer.Interfaces;
using EntityLayer.Entities;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLayer.Repositories
{
    public class Repository<T> : IRepository<T> where T : class
    {
        protected readonly AppDbContext _context;
        public DbSet<T> Entities;

        public Repository(AppDbContext context)
        {
            _context = context;
            Entities = _context.Set<T>();
        }
        public async Task<int> AddAsync(T entity)
        {
            if (entity == null)
                throw new ArgumentNullException(nameof(entity));

            await Entities.AddAsync(entity);
            return await _context.SaveChangesAsync();
        }
        public async Task<int> DeleteAsync(int id)
        {
            var entity = await GetByIdAsync(id);
            if (entity == null)
                throw new KeyNotFoundException($"Entity with ID {id} not found.");

            Entities.Attach(entity);
            Entities.Remove(entity);
            return await _context.SaveChangesAsync();
        }

        public async Task<IEnumerable<T>> GetAllAsync()
        {

            if (typeof(T) == typeof(Asset))
            {
                return await Entities.Include("FloorMap").ToListAsync();
            }
            else if (typeof(T) == typeof(AssetPositionHistory))
            {
                return await Entities.Include("Asset").Include("FloorMap").ToListAsync();
            }
            else if (typeof(T) == typeof(AssetZoneHistory))
            {
                return await Entities.Include("Asset").Include("Zone").ToListAsync();
            }
            else return await Entities.ToListAsync();
        }

        public async Task<T> GetByIdAsync(int id)
        {
            var entity = await Entities.FindAsync(id);
            if (entity == null)
                throw new KeyNotFoundException($"Entity with ID {id} not found.");

            return entity;
        }

    }
}
