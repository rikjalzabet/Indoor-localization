import { TestBed } from '@angular/core/testing';
import { WebUiService } from './web-ui.service';



describe('WebUiService', () => {
  let service: WebUiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WebUiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
