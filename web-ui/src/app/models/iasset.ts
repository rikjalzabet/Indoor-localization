export interface IAsset {
    id : number;
    name : string;
    x : number;
    y : number;
    lastSync : Date;
    floorMapId : number;
    active : boolean;
}
