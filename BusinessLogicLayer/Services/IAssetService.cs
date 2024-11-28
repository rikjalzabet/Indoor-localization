using EntityLayer.Entities;

namespace BusinessLogicLayer.Services
{
    public interface IAssetService
    {
        Task<List<Asset>> GetAllAssets();
        Task<Asset> GetAssetById(int id);
        Task<bool> AddAsset(Asset asset);
        Task<bool> UpdateAsset(Asset asset);
        bool DeleteAsset(int id);
    }
}
