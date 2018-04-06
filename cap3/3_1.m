A = [1 0 0; 2 1 0; -1 2 1];
b = [1 2 -2];
x = sist_triang_inf(A, b);

% accesso per colonna
function [b] = sist_triang_inf(A, b)
    for j = 1 : length(A)
        b(j) = b(j)/A(j,j);
        for i = j+1 : length(A)
            b(i) = b(i)-A(i,j)*b(j);
        end
    end
end
