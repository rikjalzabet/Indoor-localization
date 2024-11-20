import { Component, OnInit } from '@angular/core';
import { MatTableModule } from '@angular/material/table'; // Import MatTableModule
import { MatTableDataSource } from '@angular/material/table';
import { CommonModule } from '@angular/common'; // Required for common directives like ngFor, ngIf
import { WebUiService } from '../services/web-ui.service';
import { IAsset } from '../models/iasset';
@Component({
  selector: 'app-asset-management',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule 
  ],
  templateUrl: './asset-management.component.html',
  styleUrl: './asset-management.component.css'
})
export class AssetManagementComponent implements OnInit {
    displayedColumns: string[] = ['id', 'name', 'x', 'y', 'lastSync', 'floorMapId', 'active'];
    dataSource = new MatTableDataSource<IAsset>([]);

    constructor(private webUiService: WebUiService){}
    
    ngOnInit(): void{
        this.fetchAssets();
    }

    fetchAssets(): void {
      this.webUiService.getAssets().subscribe({
        next: (data) => (this.dataSource.data = data),
        error: (err) => console.error ('Error fetching data', err)
      });
    }
}
