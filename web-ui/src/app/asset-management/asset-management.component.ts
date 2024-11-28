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
import { MatButtonModule } from '@angular/material/button';
import { SelectionModel } from '@angular/cdk/collections';
import { MatRadioModule } from '@angular/material/radio';
import { AddAssetDialogComponent } from '../add-asset-dialog/add-asset-dialog.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { NotificationService } from '../services/notification.service';

@Component({
  selector: 'app-asset-management',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatSortModule,
    MatPaginatorModule,
    MatIconModule,
    MatButtonModule,
    MatRadioModule,
    MatDialogModule
  ],
  templateUrl: './asset-management.component.html',
  styleUrl: './asset-management.component.css'
})
export class AssetManagementComponent implements OnInit {
    displayedColumns: string[] = ['select', 'id', 'name', 'x', 'y', 'lastSync', 'floorMap', 'active'];
    dataSource = new MatTableDataSource<IAsset>([]);
    floorMaps? : IFloorMap[];
    floorMapMap = new Map<number, string>();
    selection = new SelectionModel<IAsset>(false, []);

    @ViewChild(MatSort) sort!: MatSort;
    @ViewChild(MatPaginator) paginator!: MatPaginator;

    constructor(private webUiService: WebUiService, private dialog: MatDialog, private notificationService: NotificationService){}
    
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

    deleteAsset(): void{
      const selectedAsset = this.selection.selected[0];
      this.webUiService.deleteAsset(selectedAsset.id);
      this.fetchAssets();
    }

    addAsset(): void{
      const dialogRef = this.dialog.open(AddAssetDialogComponent,{
        width: '400px',
        data: {floorMaps: this.floorMaps},
      });

      dialogRef.afterClosed().subscribe((result) =>{
        if (result)
        {
          this.webUiService.addAsset(result);
          this.fetchAssets();
          this.notificationService.showSuccess('Succcesfully added new asset!');
        }
      })
    }

    editAsset(): void{
      const selectedAsset = this.selection.selected[0];
      if(selectedAsset){
      console.log("Selected assit", selectedAsset);
      const dialogRef = this.dialog.open(AddAssetDialogComponent,{
        width: '400px',
        data: {floorMaps: this.floorMaps,
          asset: selectedAsset
        },
      });

      dialogRef.afterClosed().subscribe((result) =>{
        if (result)
        {
          this.webUiService.updateAsset(result);
          this.fetchAssets();
          this.notificationService.showSuccess('Succcesfully updated asset!');
        }
      })
    }
    else{
      this.notificationService.showError('Error: No asset was selected');
    }
  }


    
}
