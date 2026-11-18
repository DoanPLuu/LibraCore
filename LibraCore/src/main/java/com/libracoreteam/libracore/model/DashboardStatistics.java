package com.libracoreteam.libracore.model;

import java.math.BigDecimal;
import java.util.List;

public record DashboardStatistics(
        long activeBookTitles,
        long totalReaders,
        long activeReaders,
        long newReaders,
        long readersWithOutstandingLoans,
        long openLoanTickets,
        long totalBorrowedCopies,
        long totalReturnedCopies,
        long currentlyBorrowedCopies,
        long overdueCopies,
        long totalCopies,
        long availableCopies,
        long copiesOnLoan,
        long unavailableCopies,
        BigDecimal collectedFines,
        List<LoanTrendPoint> loanTrend,
        List<ReaderTrendPoint> readerTrend,
        List<BookRanking> mostBorrowedBooks,
        List<GenreInventory> inventoryByGenre) {

    public DashboardStatistics {
        loanTrend = List.copyOf(loanTrend);
        readerTrend = List.copyOf(readerTrend);
        mostBorrowedBooks = List.copyOf(mostBorrowedBooks);
        inventoryByGenre = List.copyOf(inventoryByGenre);
    }

    public enum Granularity {
        DAY("Ngày"),
        MONTH("Tháng"),
        YEAR("Năm");

        private final String label;

        Granularity(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    public record LoanTrendPoint(String period, long borrowedCopies, long returnedCopies) {}

    public record ReaderTrendPoint(String period, long readers) {}

    public record BookRanking(int bookId, String title, long borrowCount) {}

    public record GenreInventory(String genre, long totalCopies, long availableCopies, long copiesOnLoan) {}
}
