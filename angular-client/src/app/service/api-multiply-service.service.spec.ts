import { TestBed } from '@angular/core/testing';

import { ApiMultiplyServiceService } from './api-multiply-service.service';

describe('ApiMultiplyServiceService', () => {
  let service: ApiMultiplyServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ApiMultiplyServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
