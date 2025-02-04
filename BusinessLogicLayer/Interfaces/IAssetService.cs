using EntityLayer.Entities;

namespace BusinessLogicLayer.Interfaces
{
    public interface IAssetService
    {
        Task<List<Asset>> GetAllAssets();
        Task<Asset> GetAssetById(int id);
        Task<bool> AddAsset(Asset asset);
        Task<bool> UpdateAsset(Asset asset, int id);
        Task<bool> UpdateAssetPosition(AssetPositionHistory assetPositionHistory);
        Task<bool> DeleteAsset(int id);
    }
}
