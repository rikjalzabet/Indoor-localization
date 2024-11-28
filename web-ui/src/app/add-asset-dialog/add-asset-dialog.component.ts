import { Component, Inject, OnInit } from '@angular/core'; 
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field'; 
import { MatInputModule } from '@angular/material/input'; 
import { MatButtonModule } from '@angular/material/button'; 
import { MatSelectModule } from '@angular/material/select'; 
import { MatOptionModule } from '@angular/material/core';
import { FormsModule } from '@angular/forms'; 
import { IFloorMap } from '../models/IFloorMap';
import { CommonModule } from '@angular/common'; 
import { IAsset } from '../models/iasset';

@Component({
  selector: 'app-add-asset-dialog',
  standalone: true,
  imports: [
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatOptionModule,
    FormsModule, 
  ],
  templateUrl: './add-asset-dialog.component.html',
  styleUrl: `./add-asset-dialog.component.css`
})
export class AddAssetDialogComponent implements OnInit {
  assetName: string = '';
  dialogTitle: string = 'Add new asset';
  assetX: number = 0;
  assetY: number = 0;
  assetFloorMapId: number | null = null;
  floorMaps: IFloorMap[];
  asset?: IAsset;

  constructor(
    public dialogRef: MatDialogRef<AddAssetDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { floorMaps: IFloorMap[] , asset: IAsset}
  ) {
    this.floorMaps = data.floorMaps;
    this.asset = data.asset;
  }

  ngOnInit(): void{
    console.log("FLOOR MAPS ",this.floorMaps)
    console.log("ASSET",this.asset);
    if(this.asset)
    {
      this.populateAsset();
    }
  }

  populateAsset(): void{
    this.dialogTitle = 'Edit asset';
    this.assetName = this.asset?.name ?? '';
    this.assetX = this.asset?.x ?? 0;
    this.assetY = this.asset?.y ?? 0;
    this.assetFloorMapId = this.asset?.floorMapId ?? null;
  }

  save() {
    const newAsset = {
      name: this.assetName,
      x: this.assetX,
      y: this.assetY,
      floorMapId: this.assetFloorMapId
    }
    this.dialogRef.close(newAsset);
  }

  cancel(){
    this.dialogRef.close();
  }
}
