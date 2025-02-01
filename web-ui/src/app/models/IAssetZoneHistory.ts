export interface IAssetZoneHistory {
    id: number,
    assetId: number,
    zoneId: number,
    enterDateTime: Date,
    exitDateTime: Date,
    retentionTime: number
}