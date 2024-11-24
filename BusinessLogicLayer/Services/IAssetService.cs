using EntityLayer.Entities;

namespace BusinessLogicLayer.Services
{
    public interface IAssetService
    {
        List<Asset> GetAllAssets();
        Asset GetAssetById(int id);
        bool AddAsset(Asset asset);
        bool UpdateAsset(Asset asset);
        bool DeleteAsset(int id);
    }
}
