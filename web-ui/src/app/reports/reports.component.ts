import { AfterViewChecked, AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableModule, MatTableDataSource } from '@angular/material/table';
import { WebUiService } from '../services/web-ui.service';
import { IFloorMap } from '../models/IFloorMap';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { IAssetPositionHistory } from '../models/IAssetPositionHistory';
import { MatCardModule } from '@angular/material/card';
import { IAsset } from '../models/iasset';
import { SelectionModel } from '@angular/cdk/collections';
import { MatRadioModule } from '@angular/material/radio';
import * as h337 from 'heatmap.js';
import { MatButtonModule } from '@angular/material/button';
import { jsPDF } from 'jspdf';
import 'jspdf-autotable';
import { Observable } from 'rxjs';
import { title } from 'process';
import { IZones } from '../models/IZones';
import { IAssetZoneHistory } from '../models/IAssetZoneHistory';


@Component({
  selector: 'app-reports',
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatSelectModule,
    FormsModule,
    CommonModule,
    MatCardModule,
    MatIconModule,
    MatSortModule,
    MatTableModule,
    MatPaginatorModule,
    MatRadioModule,
    MatButtonModule
  ],
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css'],
})
export class ReportsComponent implements OnInit, AfterViewInit, AfterViewChecked {
  floorMaps?: IFloorMap[];
  assets?: IAsset[];
  dataSource = new MatTableDataSource<IAssetPositionHistory>([]);
  displayedColumns: string[] = ['asset', 'x', 'y', 'dateTime'];
  floorMapMap = new Map<number, string>();
  selectedFloorMapId?: number;
  assetPositionHistory?: IAssetPositionHistory[];
  assetMap = new Map<number, string>();
  selection = new SelectionModel<IAsset>(false, []);
  heatmap: any;
  zones?: IZones[];
  assetZoneHistory?: IAssetZoneHistory[];
  zoneMap = new Map<number, string>();


  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private webUiService: WebUiService) {}

  ngOnInit(): void {
    this.fetchFloorMaps();
    this.fetchZones();
    this.fetchAssetZoneHistory();
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;

   // Ensure it's created after the view is initialized
  }

  onImageLoad(): void{
    const heatmapContainer = document.getElementById('heatmapContainer');
    if (heatmapContainer && !this.heatmap) {
      this.createHeatmap();  // Only create once
    }
  
    if (this.heatmap && heatmapContainer && this.assetPositionHistory) {
      const data = {
        min: 0,
        max: 100,
        data: this.assetPositionHistory.map((item) => ({
          x: item.x,
          y: item.y,
          value: 100, // Adjust the value as needed
        })),
      };



      // Clear the existing data before setting new data
      this.heatmap.setData({ data: [] });

      // Set the new data to the heatmap
      this.heatmap.setData(data);
    }
  }

  ngAfterViewChecked(): void {
    console.log("ULAZI");
    // Ensure that the element is available before trying to update the heatmap
  
  }

  createHeatmap(): void {
    const heatmapContainer = document.getElementById('heatmapContainer');
    if (heatmapContainer) {
      console.log("Heatmap created");
      this.heatmap = h337.create({
        container: heatmapContainer,
      });
    }
    else{
      console.error("HEATMAP");
    }
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  getAssetZoneHistory(assetId: number): any[] {
    // Merge `assetZoneHistory` with zone names for easier rendering
    return (
      this.assetZoneHistory
        ?.filter((zone) => zone.assetId === assetId)
        .map((zone) => ({
          ...zone,
          name: this.zones?.find((z) => z.id === zone.zoneId)?.name || 'Unknown Zone',
        })) || []
    );
  }

  fetchFloorMaps(): void {
    this.webUiService.getFloorMaps().subscribe({
      next: (data) => {
        this.floorMaps = data;
        this.floorMaps.forEach((fm) => this.floorMapMap.set(fm.id, fm.name));
      },
      error: (err) => console.error('Error fetching data', err),
    });
  }

  fetchAssets(floorMapId: Number): void {
    this.webUiService.getAssets().subscribe({
      next: (data) => {
        this.assets = data.filter((asset) => asset.floorMapId === floorMapId);
        this.assets.forEach((ass) => this.assetMap.set(ass.id, ass.name));
      },
      error: (err) => console.error('Error fetching data', err),
    });
  }

  getAssetName(assetId: number): string {
    return this.assetMap.get(assetId) || 'Unknown';
  }

  getFloorMapImage(floorMapId?: number): string | undefined {
    if (floorMapId === undefined) {
      return undefined;
    }
    const floorMap = this.floorMaps?.find((fm) => fm.id === floorMapId);
    this.fetchAssetPositionHistory(floorMapId);
    this.fetchAssets(floorMapId);
    return floorMap?.image;
  }

  getAssetPositionHistory(assetId: number): IAssetPositionHistory[] {
    return this.assetPositionHistory?.filter((position) => position.assetId === assetId) || [];
  }

  fetchAssetPositionHistory(floorMapId: number): void {
    this.webUiService.getAssetPositionHistory(floorMapId).subscribe({
      next: (data) => {
        this.assetPositionHistory = data;
        this.dataSource.data = data;

        if (this.heatmap) {
          const heatmapData = {
            min: 0,
            max: 100,
            data: data.map(item => ({
              x: item.x,
              y: item.y,
              value: 100, // Adjust based on your logic
            })),
          };
          this.heatmap.setData(heatmapData);
        }

      },
      error: (err) => console.error('Error fetching data', err),
    });
  }

  fetchZones(): void {
    this.webUiService.getZones().subscribe({
      next: (data) => {
        this.zones = data;
      },
      error: (err) => console.error('Error fetching zones', err),
    });
  }

  fetchAssetZoneHistory(): void {
    this.webUiService.getAssetZoneHistory().subscribe({
      next: (data) => {
        this.assetZoneHistory = data;
      },
      error: (err) => console.error('Error fetching asset zone history', err),
    });
  }

  getZoneName(zoneId: number): string {
    return this.zoneMap.get(zoneId) || 'Unknown';
  }

  downloadPdf(): void {
    const doc = new jsPDF();
  
    // Asset Table
    doc.text('Assets', 10, 10);
    (doc as any).autoTable({
      startY: 15,
      head: [['ID', 'Name', 'X', 'Y', 'Last Sync', 'Active']],
      body: this.assets?.map((asset) => [
        asset.id,
        asset.name,
        asset.x,
        asset.y,
        asset.lastSync,
        asset.active ? 'Yes' : 'No',
      ]),
    });
  
    // Asset Position History Table
    doc.text('Asset Position History', 10, (doc as any).lastAutoTable.finalY + 10);
    (doc as any).autoTable({
      startY: (doc as any).lastAutoTable.finalY + 15,
      head: [['Asset Name', 'X', 'Y', 'Date']],
      body: this.assetPositionHistory?.map((item) => [
        this.getAssetName(item.assetId),
        item.x,
        item.y,
        item.dateTime,
      ]),
    });
  
    // Asset Zone History Table
    doc.text('Asset Zone History', 10, (doc as any).lastAutoTable.finalY + 10);
    (doc as any).autoTable({
      startY: (doc as any).lastAutoTable.finalY + 15,
      head: [['Asset Name', 'Zone', 'Enter Time', 'Exit Time', 'Retention Time']],
      body: this.assetZoneHistory?.map((item) => [
        this.getAssetName(item.assetId),
        this.getZoneName(item.zoneId),
        item.enterDateTime,
        item.exitDateTime,
        item.retentionTime,
      ]),
    });
  
    // Save PDF
    doc.save('Assets_' + this.selectedFloorMapId + '_' + Date.now() + '.pdf');
  }
}
