import { LoanPage } from "./LoanPage";

export const LOAN_DATA: LoanPage = {
    content: [
        { id: 1, client: { id: 1, name: 'Client 1' }, game: { id: 1, title: 'Juego 1', age: 6, category: { id: 1, name: 'Categoría 1' }, author: { id: 1, name: 'Autor 1', nationality: 'Nacionalidad 1' } }, startDate: new Date("02-02-2024"), endDate: new Date("12-02-2024")},
        { id: 2, client: { id: 2, name: 'Client 2' }, game: { id: 4, title: 'Juego 4', age: 10, category: { id: 2, name: 'Categoría 2' }, author: { id: 1, name: 'Autor 1', nationality: 'Nacionalidad 1' } }, startDate: new Date("02-02-2024"), endDate: new Date("12-02-2024")},
        { id: 3, client: { id: 3, name: 'Client 3' }, game: { id: 5, title: 'Juego 5', age: 16, category: { id: 2, name: 'Categoría 2' }, author: { id: 2, name: 'Autor 2', nationality: 'Nacionalidad 2' } }, startDate: new Date("02-02-2024"), endDate: new Date("12-02-2024")},
        { id: 4, client: { id: 4, name: 'Client 4' }, game: { id: 7, title: 'Juego 7', age: 12, category: { id: 3, name: 'Categoría 3' }, author: { id: 1, name: 'Autor 1', nationality: 'Nacionalidad 1' } }, startDate: new Date("02-02-2024"), endDate: new Date("12-02-2024")},
        { id: 5, client: { id: 5, name: 'Client 5' }, game: { id: 1, title: 'Juego 1', age: 6, category: { id: 1, name: 'Categoría 1' }, author: { id: 1, name: 'Autor 1', nationality: 'Nacionalidad 1' } }, startDate: new Date("14-02-2024"), endDate: new Date("25-02-2024")}
    ],  
    pageable : {        
        pageSize: 5,
        pageNumber: 0,
        sort: [
            {property: "id", direction: "ASC"}
        ]
    },
    totalElements: 5
}