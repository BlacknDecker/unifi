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
    e = @(x) abs(f(x) - p(x)); % errore di interp

    % GRAFICI FUNZIONI E POLINOMI
    figure(1)
    fplot(f, [a, b])
    hold on
    fplot(p, [a, b])
    plot(xi, fi, 'r*')
    hold off
    print('-dpng', strcat(num2str(n), '.png'));

    % GRAFICO DETTAGLIO ERRORE
    figure(2)
    x = linspace(5, b, 100001); % estremo destro
    plot(x, e(x), 'DisplayName', strcat('n = ', num2str(n)))
    hold on

    % TABELLA COSTANTI LEBESGUE
    x = linspace(a, b, 100001);
    leb = zeros(length(x), 1)';
    for k = 1:n+1
        leb = leb + abs(l_k_n(x, xi, k));
    end
    leb = norm(leb, inf);

    record = {n, leb};
    Table = [Table; record];

    n = n + 2;
end

% mostra la tabella
figure(3)
uitable('Data',Table{:,:},'ColumnName',Table.Properties.VariableNames,...
    'RowName',Table.Properties.RowNames,'Units', 'Normalized', 'Position',[0, 0, 1, 1]);
