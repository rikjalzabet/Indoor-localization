import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableModule } from '@angular/material/table'; // Import MatTableModule
import { MatTableDataSource } from '@angular/material/table';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { CommonModule } from '@angular/common'; // Required for common directives like ngFor, ngIf
import { WebUiService } from '../services/web-ui.service';
import { IAsset } from '../models/iasset';
import { MatIconModule } from '@angular/material/icon';
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
    displayedColumns: string[] = ['id', 'name', 'x', 'y', 'lastSync', 'floorMapId', 'active'];
    dataSource = new MatTableDataSource<IAsset>([]);

    @ViewChild(MatSort) sort!: MatSort;
    @ViewChild(MatPaginator) paginator!: MatPaginator;

    constructor(private webUiService: WebUiService){}
    
    ngOnInit(): void{
        this.fetchAssets();
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
}
