Table = cell2table(cell(0,3));
Table.Properties.VariableNames = {'n' 'iterazioni', 'autovalore'};

tol = 10^(-5);

for n = 100:100:1000
   [l, i] = potenze(matr_sparsa_es1(n), tol);

   record = {n, i, l};
   Table = [Table; record];
end

uitable('Data',Table{:,:},'ColumnName',Table.Properties.VariableNames,...
    'RowName',Table.Properties.RowNames,'Units', 'Normalized', 'Position',[0, 0, 1, 1]);

function [lambda1, it] = potenze(A, tol)
    [m, n] = size(A);
    if m ~= n
        error('la matrice deve essere quadrata')
    end

    % scelgo il vettore iniziale casualmente
    z = rand(n, 1);

    lambda1 = inf;
    err = inf;
    it = 1;

    while err > tol
        q = z / norm(z);
        z = A * q;
        lambda0 = lambda1;
        lambda1 = q'*z;
        err = abs(lambda0 - lambda1);
        it = it + 1;
    end
end
