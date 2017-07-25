import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

// material
// import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MdIconModule, MdButtonModule, MdCheckboxModule } from '@angular/material';

@NgModule({
  imports: [
    CommonModule,
    // BrowserAnimationsModule,
    MdIconModule, MdButtonModule, MdCheckboxModule
  ],
  exports: [
    MdIconModule,
    MdButtonModule,
    MdCheckboxModule
  ],
  declarations: []
})
export class CustomMaterialModule { }
