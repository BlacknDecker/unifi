Table = cell2table(cell(0,3));
Table.Properties.VariableNames = {'i' 'sqrt_alpha' 'err_ass'};

x_0 = 5;
alpha = 5;

[Table, sqrt_alpha] = secanti(alpha, x_0, 200, 1/1000000, Table);

uitable('Data',Table{:,:},'ColumnName',Table.Properties.VariableNames,...
    'RowName',Table.Properties.RowNames,'Units', 'Normalized', 'Position',[0, 0, 1, 1]);

function [Table, sqrt_alpha] = secanti(alpha, x0, imax, tolx, Table)
    x1 = (x0 + alpha/x0)/2;
    x = ( (x1^2 - alpha) * x0 - (x0^2 - alpha)*x1 ) / ((x1^2 - alpha) - (x0^2 - alpha));
    i = 1;
    row = {i, x, abs( sqrt(alpha) - x )};
    Table = [Table; row];
    while(i < imax) && (abs(x-x0)>tolx)
        x0=x1;
        x1=x;
        i = i+1;
        x = ( (x1^2 - alpha) * x0 - (x0^2 - alpha)*x1 ) / ((x1^2 - alpha) - (x0^2 - alpha));
        row = {i, x, abs( sqrt(alpha) - x )};
        Table = [Table; row];
    end
    sqrt_alpha = x;
end

