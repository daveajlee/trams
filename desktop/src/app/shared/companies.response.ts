import {CompanyResponse} from "../management/company.response";

/**
 * This class defines a model for retrieving companies from the TraMS server.
 */
export class CompaniesResponse {

    //a count of the number of companies which were found by the server.
    count: number;

    //an array of all companies found by the server.
    companyResponseList: CompanyResponse[];

}