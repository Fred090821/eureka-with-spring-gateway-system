import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { throwError } from 'rxjs';
import { ApiMultiplyServiceService } from '../service/api-multiply-service.service';

@Component({
  selector: 'app-name-editor',
  templateUrl: './name-editor.component.html',
  styleUrls: ['./name-editor.component.css']
})


export class NameEditorComponent {

  num1Control: FormControl = new FormControl('', [Validators.required, Validators.pattern(/^\d+$/)]);
  num2Control: FormControl = new FormControl('', [Validators.required, Validators.pattern(/^\d+$/)]);

  // returned data
  multiplicationResult: any;
  errorMessage!: string;
  isClicked: boolean | undefined;

  constructor(private apiMultiplyServiceService: ApiMultiplyServiceService) {
  }


  // post the two operands provided
  sendData() {
    // read from the UI app the operands input
    const num1 = parseInt(this.num1Control.value, 10);
    const num2 = parseInt(this.num2Control.value, 10);

    console.log(`num1: ${num1}, num2: ${num2}`);

    //pass the values to the object to send
    const operands = { key1: num1, key2: num2 };

    this.apiMultiplyServiceService.postOperands(operands).subscribe(
      (result: any) => {
        // Object returned with data to display
        this.multiplicationResult = result;
        this.errorMessage = '';
        console.log('Response from server returned');
      },
      (error: any) => {
        this.handleError(error);
      }
    );
  }

  //error handler and converter
  private handleError(errorRes: HttpErrorResponse) {
    console.warn(errorRes);
  
    if (errorRes.status === 400) {
      this.errorMessage = "Bad request: request is malformed or contains invalid data 400";
      console.log("***  errorRes : ", errorRes.message);
    } else if (errorRes.status === 401) {
      console.log("***  errorRes : ", errorRes.message);
      this.errorMessage = "Unauthorized: lack of proper authentication or authorization credentials or authentication server is down. 401";
    } else if (errorRes.status === 404) {
      console.log("***  errorRes : ", errorRes.message);
      this.errorMessage = "Not Found: The requested resource could not be found on the server. 404";
    } else if (errorRes.status === 429) {
      console.log("***  errorRes : ", errorRes.message);
      this.errorMessage = "Too Many Requests 429";
    } else if (errorRes.status === 500) {
      console.log("***  errorRes : ", errorRes.message);
      this.errorMessage = "Internal Server Error: The server encountered an unexpected error while processing the request or no instance server is available. 500";
    }  else if (errorRes.status === 0) {
      console.log("***  errorRes : ", errorRes.message);
      this.errorMessage = "net::ERR_CONNECTION_REFUSED: There seem to be a network issue";
    } else if (errorRes.status === undefined) {
      console.log("***  errorRes : ", errorRes.message);
      this.errorMessage = "net::ERR_CONNECTION_REFUSED: The Network, The gateway or other servers might be down";
    }else {
      console.log("***  errorRes : ", errorRes.message);
      this.errorMessage = "Invalid operand(s) may have been provided.";
    }
  
    if (errorRes.error instanceof ErrorEvent) {
      this.errorMessage = "Looks like there is a network issue.";
    }
  }
}