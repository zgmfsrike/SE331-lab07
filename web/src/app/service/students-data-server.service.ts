import {Injectable} from '@angular/core';
import {Student} from '../students/student';
import {Http, Response} from '@angular/http';
import {StudentsDataService} from './students-data.service';

@Injectable()
export class StudentsDataServerService {
  constructor(private http: Http) {

  }

  getStudentsData() {
    let studentArray: Student[];
    return this.http.get('http://localhost:8080/student')
      .map(res => res.json());
  }

  getStudent(id: number) {
    let student:Student;
    return this.http.get('http://localhost:8080/student/'+id)
      .map((res:Response) => {
        if (res){
          if (res.status === 2000){
            return res.json();
          }
          if (res.status === 204){
            return null;
          }
        }
      });
  }
}
