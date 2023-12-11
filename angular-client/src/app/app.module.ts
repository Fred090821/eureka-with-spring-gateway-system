import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { NameEditorComponent } from './name-editor/name-editor.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { ErrorIntercept } from './error-interceptor.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    NameEditorComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorIntercept,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
