import { createContext, useState } from "react";

export const AssignContext = createContext({
    savedAssignments: [],
    addAssignment: (assignment) => {},
    removeAssignment: (routeNumber, tourNumber) => {},
    addAdditionalTour: (routeNumber, tourNumber) => {},
    savedAdditionalTours: []
});

function AssignContextProvider({children}) {
    const [assignments, setAssignments] = useState([]);
    const [additionalTours, setAdditionalTours] = useState([]);

    function addAssignment(assignment) {
        setAssignments((currentAssignments) => [...currentAssignments, assignment]);
    }

    function removeAssignment(routeNumber, tourNumber) {

        var counter = 0;

        assignments.forEach((assignment) => {
            if ( assignment.routeNumber === routeNumber && assignment.tourNumber === tourNumber ) {
                const value = assignments.splice(counter, 1);
            } else {
                counter++;
            }
        })
    }

    function addAdditionalTour(routeNumber, tourNumber) {
        setAdditionalTours((additionalTours) => [...additionalTours, routeNumber + '/' + tourNumber]);
    }

    const value = {
        savedAssignments: assignments,
        savedAdditionalTours: additionalTours,
        addAssignment: addAssignment,
        removeAssignment: removeAssignment,
        addAdditionalTour: addAdditionalTour
    }
    
    return <AssignContext.Provider value={value}>{children}</AssignContext.Provider>
}

export default AssignContextProvider;