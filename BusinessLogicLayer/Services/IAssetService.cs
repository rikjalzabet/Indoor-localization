using EntityLayer.Entities;

namespace BusinessLogicLayer.Services
{
    public interface IAssetService
    {
        Task<List<Asset>> GetAllAssets();
        Asset GetAssetById(int id);
        Task<bool> AddAsset(Asset asset);
        bool UpdateAsset(Asset asset);
        bool DeleteAsset(int id);
    }
}
