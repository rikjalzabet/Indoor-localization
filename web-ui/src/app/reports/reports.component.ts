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
import { ActivatedRoute, Router } from '@angular/router';
import * as h337 from 'heatmap.js';
import { MatButtonModule } from '@angular/material/button';
import { jsPDF } from 'jspdf';
import 'jspdf-autotable';
import { Observable } from 'rxjs';
import { IAssetZoneHistory } from '../models/IAssetZoneHistory';
import { IZone } from '../models/IZone';

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
export class ReportsComponent implements OnInit, AfterViewInit {
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
  zones?: IZone[];
  assetZoneHistory?: IAssetZoneHistory[];
  zoneMap = new Map<number, string>();

  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private webUiService: WebUiService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.fetchFloorMaps();
    this.fetchAssetZoneHistory();
    this.route.paramMap.subscribe(params => {
      const floorMapId = Number(params.get('floorMapId'));
      if (floorMapId) {
        this.selectedFloorMapId = floorMapId;
        this.fetchAssetPositionHistory(floorMapId);
        this.fetchAssets(floorMapId);
        this.fetchZones();
        this.getAssetZoneHistory(floorMapId);
      }
    });
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  onImageLoad(): void {
    const heatmapContainer = document.getElementById('heatmapContainer');
    if (heatmapContainer && !this.heatmap) {
      this.createHeatmap();
    }

    if (this.heatmap && heatmapContainer && this.assetPositionHistory) {
      const data = {
        min: 0,
        max: 100,
        data: this.assetPositionHistory.map((item) => {
          const { left, top } = this.getAssetPosition(item);
          return { x: left, y: top, value: 80 };
        }),
      };

      // Clear the existing data before setting new data
      this.heatmap.setData({ data: [] });

      // Set the new data to the heatmap
      this.heatmap.setData(data);
    }
  }

  createHeatmap(): void {
    const heatmapContainer = document.getElementById('heatmapContainer');
    if (heatmapContainer) {
      console.log("Heatmap created");
      this.heatmap = h337.create({
        container: heatmapContainer,
      });
    } else {
      console.error("HEATMAP");
    }
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  updateHeatmap(): void {
    if (this.heatmap && this.assetPositionHistory) {
      const data = {
        min: 0,
        max: 100,
        data: this.assetPositionHistory.map((item) => {
          const { left, top } = this.getAssetPosition(item);
          return { x: left, y: top, value: 80 };
        }),
      };
      this.heatmap.setData(data);
      console.log(data);
    }
  }

  getAssetZoneHistory(assetId: number): any[] {
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
    this.webUiService.getFloorMaps().subscribe(
      (data) => { this.floorMaps = data; },
      (error) => { console.error('Error fetching floor maps:', error); }
    );
  }

  fetchAssets(floorMapId: Number): void {
    this.webUiService.getAssets().subscribe(
      (data) => { this.assets = data.filter((asset) => asset.floorMapId === floorMapId); },
      (error) => { console.error('Error fetching assets:', error); }
    );
  }

  getAssetName(assetId: number): string {
    return this.assetMap.get(assetId) || 'Unknown';
  }

  getFloorMapImage(floorMapId?: number): string | undefined {
    return this.floorMaps?.find((fm) => fm.id === floorMapId)?.image;
  }

  getAssetPositionHistory(assetId: number): IAssetPositionHistory[] {
    return this.assetPositionHistory?.filter((position) => position.assetId === assetId) || [];
  }

  fetchAssetPositionHistory(floorMapId: number): void {
    this.webUiService.getAssetPositionHistory(floorMapId).subscribe(
      (data) => {
        this.assetPositionHistory = data;
        this.dataSource.data = data;
        if (this.heatmap) {
          this.updateHeatmap();
        }
      },
      (error) => { console.error('Error fetching asset position history:', error); }
    );
  }

  updateFloorMapSelection(): void {
    if (this.selectedFloorMapId) {
      this.router.navigate(['/reports', this.selectedFloorMapId], {
        queryParamsHandling: 'merge',
      });
      this.fetchAssetPositionHistory(this.selectedFloorMapId);
      this.fetchAssets(this.selectedFloorMapId);
    }
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

  getAssetPosition(asset: IAssetPositionHistory): { top: number; left: number } {
    const imageWidth = 780;
    const imageHeight = 610; 

    const gridColumns = 100; 
    const gridRows = 100; 

    const marginTop = 50;
    const marginBottom = 50;
    const marginLeft = 50;
    const marginRight = 50; 

    const adjustedWidth = imageWidth - marginLeft - marginRight;
    const adjustedHeight = imageHeight - marginTop - marginBottom;

    const left = Math.round(marginLeft + (asset.x / gridColumns) * adjustedWidth);
    const top = Math.round(marginTop + (asset.y / gridRows) * adjustedHeight);

    return { top, left };
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
