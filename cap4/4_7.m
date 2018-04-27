runge = @(x) 1/(1+25*x^2);
a = -6;
b = 6;

fplot(runge, [a, b])
print('-dpng','runge.png')

% tabella di comodo per mostrare le stime dell'errore
Table = cell2table(cell(0,2));
Table.Properties.VariableNames = {'x' 'error'};

n = 2;
while n<=40
    xi = ceby(n, a, b);
    fi = zeros(length(xi), 1);

    for i = 1:length(xi)
        fi(i) = feval(runge, xi(i));
    end

    p = @(x) lagrange(xi, fi, x);
    fplot(p, [a, b])
    print('-dpng', strcat(num2str(n), '.png'));

   % il max e' in zero per tutti i p(x)
   % quindi evito il calcolo dela norma 0 e valuto semplicemente in 0
    syms x
    error = abs(subs(diff(1/(1+25*x^2), n), 0)/factorial(n+1)*2^n);
    iteration = {n, double(error)};
    Table = [Table; iteration];

    n = n + 2;
end

% mostra la tabella
uitable('Data',Table{:,:},'ColumnName',Table.Properties.VariableNames,...
    'RowName',Table.Properties.RowNames,'Units', 'Normalized', 'Position',[0, 0, 1, 1]);
