import { Routes } from '@angular/router';
import { AssetManagementComponent } from './asset-management/asset-management.component';

export const routes: Routes = [
    { path: '', redirectTo: 'asset-managment', pathMatch: 'full'},
    { path: 'asset-managment', component: AssetManagementComponent },
];
