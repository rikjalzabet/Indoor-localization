using EntityLayer.Entities;

namespace BusinessLogicLayer
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
