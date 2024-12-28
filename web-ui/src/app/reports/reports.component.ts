import { AfterViewChecked, AfterViewInit, Component, OnInit,ViewChild } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableModule,MatTableDataSource } from '@angular/material/table';
import { WebUiService } from '../services/web-ui.service';
import { IFloorMap } from '../models/IFloorMap';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { IAssetPositionHistory } from '../models/IAssetPositionHistory';
import {MatCardModule} from '@angular/material/card';
import { IAsset } from '../models/iasset';
import { SelectionModel } from '@angular/cdk/collections';
import { MatRadioModule } from '@angular/material/radio';
import * as h337 from 'heatmap.js';

@Component({
  selector: 'app-reports',
  standalone: true,
  imports: [ MatFormFieldModule,
    MatSelectModule,
    FormsModule,
  CommonModule,
  MatCardModule,
  MatIconModule,
  MatSortModule,
  MatTableModule,
  MatPaginatorModule,
  MatRadioModule
  ,],
  templateUrl: './reports.component.html',
  styleUrl: './reports.component.css'
})
export class ReportsComponent implements OnInit,AfterViewInit,AfterViewChecked{
  floorMaps? : IFloorMap[];
  assets? : IAsset[];
  dataSource = new MatTableDataSource<IAssetPositionHistory>([]);
  displayedColumns: string[] = ['asset', 'x', 'y', 'dateTime'];
  floorMapMap = new Map<number, string>();
  selectedFloorMapId?: number;
  assetPositionHistory? : IAssetPositionHistory[];
  assetMap = new Map<number,string>();
  selection = new SelectionModel<IAsset>(false, []);
  heatmap: any;

  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private webUiService: WebUiService){}

  ngOnInit(): void {
    this.fetchFloorMaps();
  }

  ngAfterViewChecked() : void{
    if (!this.heatmap) {
      this.createHeatmap();
    }

    // Prepare the data for the heatmap
    const data = {
      min: 0,
      max: 100,
      data: this.assetPositionHistory?.map(item => ({
        x: item.x,
        y: item.y,
        value: 10,  // Adjust the value as needed
      })) ?? [],
    };

    console.log("PODACI", data);
    // Clear the existing data
    this.heatmap.setData({ data: [] });

    // Set the new data to the heatmap
    this.heatmap.setData(data);
  }

  createHeatmap(): void {
    this.heatmap = h337.create({
      container: document.getElementById('heatmapContainer') as HTMLElement,
    });
  }


  ngAfterViewInit() : void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
}


applyFilter(event: Event): void {
  const filterValue = (event.target as HTMLInputElement).value;
  this.dataSource.filter = filterValue.trim().toLowerCase();
}


  fetchFloorMaps(): void {
    this.webUiService.getFloorMaps().subscribe({
      next: (data) => {
        this.floorMaps = data;
        this.floorMaps.forEach(fm => this.floorMapMap.set(fm.id, fm.name));
      },
      error: (err) => console.error('Error fetching data', err)
    });
  }

  fetchAssets(): void {
    this.webUiService.getAssets().subscribe({
      next: (data) => {this.assets = data;
        this.assets.forEach(ass => this.assetMap.set(ass.id,ass.name));
      },
      error: (err) => console.error ('Error fetching data', err)
    });
  }

  getAssetName(assetId: number): string {
    return this.assetMap.get(assetId) || 'Unknown' ;
  }

  getFloorMapImage(floorMapId?: number): string | undefined {
    if (floorMapId === undefined) {
      return undefined;
    }
    const floorMap = this.floorMaps?.find((fm) => fm.id === floorMapId);
    this.fetchAssetPositionHistory(floorMapId);
    this.fetchAssets();
    return floorMap?.image;
  }

  fetchAssetPositionHistory(floorMapId : number): void{
    this.webUiService.getAssetPositionHistory(floorMapId).subscribe({
      next: (data) => {
        this.assetPositionHistory = data;
        this.dataSource.data = data;
      },
      error: (err) => console.error('Error fetching data', err)
    });
    console.log(this.assetPositionHistory);
  }

}
