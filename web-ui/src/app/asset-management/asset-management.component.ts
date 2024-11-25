import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableModule } from '@angular/material/table'; // Import MatTableModule
import { MatTableDataSource } from '@angular/material/table';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { CommonModule } from '@angular/common'; // Required for common directives like ngFor, ngIf
import { WebUiService } from '../services/web-ui.service';
import { IAsset } from '../models/iasset';
import { MatIconModule } from '@angular/material/icon';
import { IFloorMap } from '../models/IFloorMap';
@Component({
  selector: 'app-asset-management',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatSortModule,
    MatPaginatorModule,
    MatIconModule
  ],
  templateUrl: './asset-management.component.html',
  styleUrl: './asset-management.component.css'
})
export class AssetManagementComponent implements OnInit {
    displayedColumns: string[] = ['id', 'name', 'x', 'y', 'lastSync', 'floorMap', 'active'];
    dataSource = new MatTableDataSource<IAsset>([]);
    floorMaps? : IFloorMap[];
    floorMapMap = new Map<number, string>();

    @ViewChild(MatSort) sort!: MatSort;
    @ViewChild(MatPaginator) paginator!: MatPaginator;

    constructor(private webUiService: WebUiService){}
    
    ngOnInit(): void{
        this.fetchAssets();
        this.fetchFloorMaps();
    }

    ngAfterViewInit() {
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

    fetchAssets(): void {
      this.webUiService.getAssets().subscribe({
        next: (data) => (this.dataSource.data = data),
        error: (err) => console.error ('Error fetching data', err)
      });
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

    getFloorMapName(floorMapId: number): string {
      return this.floorMapMap.get(floorMapId) || 'Unknown';
    }
}
