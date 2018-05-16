f = @(x) 1./(1 + x.^2);
a = -6;
b = 6;

Table = cell2table(cell(0,2));
Table.Properties.VariableNames = {'n' 'lebesgue'};

n = 2;
while n <= 40

    xi = linspace(a, b, n + 1); % ascisse equispaziate
    fi = f(xi); % valutazioni fun di runge
    p = @(x) lagrange(xi, fi, x);

    % GRAFICI
    fplot(f, [a, b])
    hold on
    fplot(p, [a, b])
    plot(xi, fi, 'r*')
    hold off
    print('-dpng', strcat(num2str(n), '.png'));

    % STIMA DELLE COSTANTI DI LEBESGUE
    leb = (2^(n+1)) / (exp(1)*n*log(n));
    record = {n, leb};
    Table = [Table; record];

    n = n + 2;
end

% mostra la tabella
uitable('Data',Table{:,:},'ColumnName',Table.Properties.VariableNames,...
    'RowName',Table.Properties.RowNames,'Units', 'Normalized', 'Position',[0, 0, 1, 1]);
