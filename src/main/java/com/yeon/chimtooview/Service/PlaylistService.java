package com.yeon.chimtooview.Service;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPQLQuery;
import com.yeon.chimtooview.Entity.Playlist;
import com.yeon.chimtooview.Entity.QPlaylist;
import com.yeon.chimtooview.Exceptions.ExceptionMessage;
import com.yeon.chimtooview.Exceptions.NotFoundPlaylistException;
import com.yeon.chimtooview.Repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService extends QuerydslRepositorySupport {
    public PlaylistService() { super(Playlist.class); }

    @Autowired
    private PlaylistRepository playlistRepository;

    public Playlist getPlaylistById(String id) throws NotFoundPlaylistException {
        Playlist playlist = playlistRepository.findById(id).orElse(null);
        if(playlist == null) throw new NotFoundPlaylistException(ExceptionMessage.NF_PLAYLIST);

        return playlist;
    }

    public List<Playlist> getPlaylistAll() {
        return playlistRepository.findAll();
    }

    public long getPlaylistAllCountByFilter(int ownerId, String searchWord) {
        QPlaylist qPlaylist = QPlaylist.playlist;

        JPQLQuery<Playlist> query = from(qPlaylist).where(qPlaylist.owners.eq(ownerId));

        if(!searchWord.equals("__BLANK")) {
            query = query.where(qPlaylist.title.contains(searchWord));
        }

        long count = query.fetchCount();

        return count;
    }

    public List<Playlist> getPlaylistAllOrderByPaging(int ownerId, int category, int order, String searchWord, int pageCurrent) {
        QPlaylist qPlaylist = QPlaylist.playlist;

        // 쿼리문
        JPQLQuery<Playlist> query = from(qPlaylist).where(qPlaylist.owners.eq(ownerId));

        // 검색어 조건
        if(!searchWord.equals("__BLANK")) {
            query = query.where(qPlaylist.title.contains(searchWord));
        }

        // 순서 정렬
        OrderSpecifier orderSpecifier = null;

        if(category >= 100 && category <= 105) {
            NumberPath numberPath = null;

            switch (category) {
                case 100: // 조회수 합계
                    numberPath = qPlaylist.viewCountSum;
                    System.out.println("### viewCountSum");
                    break;
                case 101: // 조회수 평균
                    numberPath = qPlaylist.viewCountAvg;
                    System.out.println("### viewCountAvg");
                    break;
                case 102: // 좋아요 합계
                    numberPath = qPlaylist.likeCountSum;
                    System.out.println("### likeCountSum");
                    break;
                case 103: // 좋아요 평균
                    numberPath = qPlaylist.likeCountAvg;
                    System.out.println("### likeCountAvg");
                    break;
                case 104: // 싫어요 합계
                    numberPath = qPlaylist.dislikeCountSum;
                    System.out.println("### dislikeCountSum");
                    break;
                case 105: // 싫어요 평균
                    numberPath = qPlaylist.dislikeCountAvg;
                    System.out.println("### dislikeCountAvg");
                    break;
            }

            orderSpecifier = (order == 0) ? numberPath.asc() : numberPath.desc();
        } else if(category == 0) {  // 날짜
            orderSpecifier = (order == 0) ? qPlaylist.publishedAt.asc() : qPlaylist.publishedAt.desc();
        }

        // 필터, 정렬된 결과 List
        List<Playlist> playlistList = query.orderBy(orderSpecifier).offset(50 * (pageCurrent-1)).limit(50).fetch();

        return playlistList;
    }

    public Playlist postPlaylist(Playlist playlist) {
        Playlist playlistFromDb = playlistRepository.findById(playlist.getId()).orElse(null);

        Playlist result = null;

        if(playlistFromDb == null) {


            System.out.println("@@@@@@ " + playlist.toString());

            result = playlistRepository.save(playlist);
        } else {
            result = playlistFromDb;
        }

        return result;
    }

    public void deletePlaylistAll() {
        playlistRepository.deleteAll();

        System.out.println("### DELETE" );
    }
}
